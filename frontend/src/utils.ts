export function filterNull<A>(a: A): a is NonNullable<A> {
  return a !== null;
}
